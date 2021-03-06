var uniqFiles = [];

$(document).ready(function () {

    function centerModal() {
        $(this).css('display', 'block');
        var $dialog = $(this).find(".modal-dialog");
        var offset = ($(window).height() - $dialog.height()) / 2;
        // Center modal vertically in window
        $dialog.css("margin-top", offset);
    }

    $('.modal').on('show.bs.modal', centerModal);
    $(window).on("resize", function () {
        $('.modal:visible').each(centerModal);
    });


    $(function () {
        $("li").removeClass('active');
        $("#linkToUtils").addClass('selected');
        $("#linkToCSVFileUploading").addClass('active');
        $("#linkToFile").addClass('active');
    });

    $('input:file').on('change', function (evt) {
        var files = evt.target.files;

        uniqFiles = files;
        $('#list').html('');
        for (var h = 0, q; q = uniqFiles[h]; h++) {
            var sub = (q.name).substring(0, 20);
            $('#list').append('<li class="list-group-item" value="' + q.size + '"' + '><a class="deleting"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a><strong> ' + sub + '...</strong> <b>File type:</b> ' + (q.type || 'n/a') + ' - ' +
                q.size + ' bytes, last modified: ' +
                (q.lastModifiedDate ? q.lastModifiedDate.toLocaleDateString() : 'n/a') +
                '</li>');
        }
    });

    $('html').on('click', '.glyphicon-remove', function () {
        var $li = $(this).closest('li');
        var conf = confirm("Are you sure?");
        if (!conf) {
            console.log('decline');
        } else {
            $li.fadeOut('slow', function () {
                $li.remove();
            });
            uniqFiles = [];
        }
    });

    $('.btn-toolbar').on('click', function () {
        var data = new FormData();

        if (uniqFiles.length == 0) {
            $('#myModal').modal("hide");
            document.getElementById('errorSelectFile').style.display = "block";
            setTimeout(function () {
                    $("#errorSelectFile").fadeOut(15000);
            });
            return false;
        }

        var selectedVal = $('#csvFiles').find(":selected").val();
        for (var i = 0; i < uniqFiles.length; i++) {
            data.append(i, uniqFiles[i]);
        }


        $.ajax({
            dataType: 'json',
            url: "uploadcsvfile?flag="+selectedVal,
            data: data,
            type: "POST",
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            success: function (result) {
                $('#myModal').modal('hide');

                if (result == "SUCCESS") {
                    document.getElementById('successMessage').style.display = "block";
                    uniqFiles = [];
                    setTimeout(function () {
                        $("#successMessage").fadeOut(3000);
                        $("#list li").fadeOut(3000);
                    });
                    $('body').scrollTop(0);

                } else if (result == "INCORRECTTYPE") {
                    document.getElementById('errorIncorrectType').style.display = "block";
                    setTimeout(function () {
                        $("#errorIncorrectType").fadeOut(15000);
                    });

                } else if (result == "ERROR") {
                    document.getElementById('errorMessage').style.display = "block";
                    setTimeout(function () {
                        $("#errorMessage").fadeOut(15000);
                    });

                }else if(result == "BUSY"){
                    document.getElementById('errorMessageCSVBUSY').style.display = "block";
                    setTimeout(function () {
                        $("#errorMessageCSVBUSY").fadeOut(15000);
                    });
                } else {
                    document.getElementById('errorUnavailable').style.display = "block";
                    setTimeout(function () {
                        $("#errorUnavailable").fadeOut(15000);
                    });

                }
            }
        });

    });

    $('#generateReport').on('click', function getNameValue() {
        var values = [];
        var year= $('#yearSelect option:selected').val();
        var month = $('#monthSelect option:selected').val();
        values.push(year);
        values.push(month);
        $.each($(".check-box-table-cell:checked"),
            function () {
                var tr = $(this).closest("tr");
                values.push(tr.attr('id'));
                tr.removeClass("info");
                tr.addClass("success");
                $(this).attr('checked', false);
            });
        if(values.length < 3){
            document.getElementById('errorMessageReportChoose').style.display = "block";
            $("#errorMessageReportChoose").fadeOut(15000);
        }else {
            reportCreatingRequest(values)
        }
        var interval =  setInterval(getProgress, 2000);
    });

    $('.unDefaultTDStyle').on('click', function () {
        var checked = $(this).parents('tr').find('input[type="checkbox"]').prop('checked');
        if (checked) {
            $(this).parents('tr').find('input[type="checkbox"]').prop('checked', false);
            $(this).closest("tr").removeClass("info");

        }
        else {
            $(this).parents('tr').find('input[type="checkbox"]').prop('checked', true);
            $(this).closest("tr").addClass("info")
        }
    });

    function reportCreatingRequest(reportNames) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify(reportNames),
            url: 'reportCreating',
            success: function (data) {
                if (data == "SUCCESS") {
                    document.getElementById('successMessageReport').style.display = "block";
                    $("#successMessageReport").fadeOut(5000, function () {
                        $("#successMessageReport").fadeOut(3000);
                        location.reload();
                    });
                } else {
                    document.getElementById('errorMessageReport').style.display = "block";
                    setTimeout(function () {
                        $("#errorMessageReport").fadeOut(15000);
                    });
                }
            }
        })
    }

    $('.check-box-table-cell').click(function () {
        var checked = $(this).attr('checked');
        if (checked) {
            $(this).attr('checked', false);
            $(this).closest("tr").removeClass("info");

        }
        else {
            $(this).attr('checked', true);
            $(this).closest("tr").addClass("info")
        }
    });

    $('#selectAllBtn').on('click', function () {
        var $checkboxesAll = $('.check-box-table-cell');
        var $checkboxesChecked = $('.check-box-table-cell:checked');
        var boolCondition = !$checkboxesAll.length == $checkboxesChecked.length;
        $checkboxesAll.prop('checked', boolCondition);
        $($checkboxesAll).closest('tr')[boolCondition ? 'addClass' : 'removeClass']('info');
    });
    $.ajax({
        type: "Post",
            url: 'uploadcsvfile/generateFileTree',
        success: function (data) {
            console.log("filetree:" + data);
            $('#fileTree').fileTree({
                data: data,
                sortable: false,
                selectable: false
            });
        }
    });

    $('#fileTree').on('click', '.file', function () {
        var selected = $(this).hasClass('selected');
        if (selected) {
            $(this).removeClass('selected');
        } else {
            $(this).addClass('selected');
            var id = $(this).attr('data-id');
            window.location.href = 'downloadFile?fileId=' + id;
            $.ajax({
                type: "GET",
                url: 'downloadFile?fileId=' + id,
                contentType: "application/json",
                dataType: 'json'
            });
            setTimeout(function () {
                $('.file').removeClass("selected");
            }, 1500);
        }
    });

    $('#fileTree').on('click', '.glyphicon-download-alt', function () {
        var directoryName = $(this).attr('name');
        window.location.href = 'downloadZIP?directoryName=' + directoryName;
        $.ajax({
            type: "GET",
            url: 'downloadZIP?directoryName=' + directoryName,
            contentType: "application/json",
            dataType: 'json'
        });
    });

    function getProgress(){
        $.ajax({
            url: "./uploadcsvfile/reportCreatingProgress",
            type: "GET",
            success : function(data){
                var width = (data);
                if(data == 0){
                    clearInterval(interval);
                }
                if(data >0 && data < 100){
                    document.getElementById('progress').style.display = "block";
                    $('.progress-bar').css('width', data+'%').attr('aria-valuenow', data);
                }if (data == 100){
                    $('.progress-bar').css('width', data+'%').attr('aria-valuenow', data);
                    document.getElementById('successMessageReport').style.display="block";
                    setTimeout(function() {
                        $("#successMessageReport").fadeOut(5000);
                        $("#progress-bar").fadeOut(5000);
                        location.reload();
                    },6000);
                }
            }
        })
    }

    var interval =  setInterval(getProgress, 2000);

    var folder = $('#iconHover');
    $("#iconHover, #reportsList").hover(
        function () {
            $(folder).toggleClass('glyphicon-th-large', false);
            $(folder).toggleClass('glyphicon-th-list', true);
        },
        function () {
            $(folder).toggleClass('glyphicon-th-large', true);
            $(folder).toggleClass('glyphicon-th-list', false);
        }
    );

    var $reportModal = $('#reportModal');

    $reportModal.on('hidden.bs.modal', function () {
        $(folder).toggleClass('glyphicon-th-large', true);
        $(folder).toggleClass('glyphicon-th-list', false);
    });

    $reportModal.on('shown.bs.modal', function(){
        $(folder).toggleClass('glyphicon-th-large', false);
        $(folder).toggleClass('glyphicon-th-list', true);
    })

});
