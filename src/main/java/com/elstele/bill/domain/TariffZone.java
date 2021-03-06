package com.elstele.bill.domain;

import com.elstele.bill.tariffFileParser.fileTemplates.TariffFileTemplateData;
import com.elstele.bill.domain.common.CommonDomainBean;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tariff_zones", schema = "public")
public class TariffZone extends CommonDomainBean {

    @Basic
    @Column(name = "zone_id")
    @Generated(GenerationTime.INSERT)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int zoneId;
    @Basic
    @Column(name = "zone_name", length = 120)
    private String zoneName;
    @Basic
    @Column(name = "additional_kode")
    private String additionalKode;
    @Basic
    @Column(name = "dollar")
    private boolean dollar;
    @Basic
    @Column(name = "pref_profile")
    private int prefProfile;
    @Basic
    @Column(name = "tarif")
    private float tarif;
    @Basic
    @Column(name = "tarif_pref")
    private Float tarifPref;

    private Date validFrom;
    private Date validTo;

    public TariffZone() {
    }

    public TariffZone(TariffFileTemplateData transientTemplate) {
        this.zoneName = transientTemplate.getDirectionName();
        this.dollar = true;
        this.prefProfile = transientTemplate.getProfileId();
        this.validFrom = transientTemplate.getValidFrom();
        this.validTo = transientTemplate.getValidTo();
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

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TariffZone)) return false;

        TariffZone that = (TariffZone) o;

        if (zoneId != that.zoneId) return false;
        if (dollar != that.dollar) return false;
        if (prefProfile != that.prefProfile) return false;
        if (Float.compare(that.tarif, tarif) != 0) return false;
        if (zoneName != null ? !zoneName.equals(that.zoneName) : that.zoneName != null) return false;
        if (additionalKode != null ? !additionalKode.equals(that.additionalKode) : that.additionalKode != null)
            return false;
        if (tarifPref != null ? !tarifPref.equals(that.tarifPref) : that.tarifPref != null) return false;
        if (validFrom != null ? !validFrom.equals(that.validFrom) : that.validFrom != null) return false;
        return !(validTo != null ? !validTo.equals(that.validTo) : that.validTo != null);

    }

    @Override
    public int hashCode() {
        int result = zoneId;
        result = 31 * result + (zoneName != null ? zoneName.hashCode() : 0);
        result = 31 * result + (additionalKode != null ? additionalKode.hashCode() : 0);
        result = 31 * result + (dollar ? 1 : 0);
        result = 31 * result + prefProfile;
        result = 31 * result + (tarif != +0.0f ? Float.floatToIntBits(tarif) : 0);
        result = 31 * result + (tarifPref != null ? tarifPref.hashCode() : 0);
        result = 31 * result + (validFrom != null ? validFrom.hashCode() : 0);
        result = 31 * result + (validTo != null ? validTo.hashCode() : 0);
        return result;
    }
}
