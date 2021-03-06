package com.elstele.bill.form;

import java.util.Date;

public class TariffZoneForm {
    private int id;
    private int zoneId;
    private String zoneName;
    private String additionalKode;
    private boolean dollar;
    private int prefProfile;
    private float tarif;
    private Float tarifPref;

    private Long validFrom;
    private Long validTo;

    private Date validFromAsDate;
    private Date validToAsDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getAdditionalKode() {
        return additionalKode;
    }

    public void setAdditionalKode(String additionalKode) {
        this.additionalKode = additionalKode;
    }

    public boolean isDollar() {
        return dollar;
    }

    public void setDollar(boolean dollar) {
        this.dollar = dollar;
    }

    public int getPrefProfile() {
        return prefProfile;
    }

    public void setPrefProfile(int prefProfile) {
        this.prefProfile = prefProfile;
    }

    public float getTarif() {
        return tarif;
    }

    public void setTarif(float tarif) {
        this.tarif = tarif;
    }

    public Float getTarifPref() {
        return tarifPref;
    }

    public void setTarifPref(Float tarifPref) {
        this.tarifPref = tarifPref;
    }

    public Long getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Long validFrom) {
        this.validFrom = validFrom;
    }

    public Long getValidTo() {
        return validTo;
    }

    public void setValidTo(Long validTo) {
        this.validTo = validTo;
    }

    public Date getValidFromAsDate() {
        return validFromAsDate;
    }

    public void setValidFromAsDate(Date validFromAsDate) {
        this.validFromAsDate = validFromAsDate;
    }

    public Date getValidToAsDate() {
        return validToAsDate;
    }

    public void setValidToAsDate(Date validToAsDate) {
        this.validToAsDate = validToAsDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TariffZoneForm)) return false;

        TariffZoneForm that = (TariffZoneForm) o;

        if (id != that.id) return false;
        if (zoneId != that.zoneId) return false;
        if (dollar != that.dollar) return false;
        if (prefProfile != that.prefProfile) return false;
        if (Float.compare(that.tarif, tarif) != 0) return false;
        if (zoneName != null ? !zoneName.equals(that.zoneName) : that.zoneName != null) return false;
        if (additionalKode != null ? !additionalKode.equals(that.additionalKode) : that.additionalKode != null)
            return false;
        if (tarifPref != null ? !tarifPref.equals(that.tarifPref) : that.tarifPref != null) return false;
        if (validFrom != null ? !validFrom.equals(that.validFrom) : that.validFrom != null) return false;
        if (validTo != null ? !validTo.equals(that.validTo) : that.validTo != null) return false;
        if (validFromAsDate != null ? !validFromAsDate.equals(that.validFromAsDate) : that.validFromAsDate != null)
            return false;
        return !(validToAsDate != null ? !validToAsDate.equals(that.validToAsDate) : that.validToAsDate != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + zoneId;
        result = 31 * result + (zoneName != null ? zoneName.hashCode() : 0);
        result = 31 * result + (additionalKode != null ? additionalKode.hashCode() : 0);
        result = 31 * result + (dollar ? 1 : 0);
        result = 31 * result + prefProfile;
        result = 31 * result + (tarif != +0.0f ? Float.floatToIntBits(tarif) : 0);
        result = 31 * result + (tarifPref != null ? tarifPref.hashCode() : 0);
        result = 31 * result + (validFrom != null ? validFrom.hashCode() : 0);
        result = 31 * result + (validTo != null ? validTo.hashCode() : 0);
        result = 31 * result + (validFromAsDate != null ? validFromAsDate.hashCode() : 0);
        result = 31 * result + (validToAsDate != null ? validToAsDate.hashCode() : 0);
        return result;
    }
}
