package ru.sw.modules.weapon;

import ru.sw.platform.core.annotations.ModuleInfo;
import ru.sw.platform.core.entity.AbstractEntity;
import ru.sw.platform.core.entity.UserList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "weapon")
@ModuleInfo(repositoryName = "WeaponRepository" , serviceName = "WeaponService")
public class Weapon extends AbstractEntity {

    @Column(length = 500 , name="weapon_name")
    private String weaponName;


    @Column(length = 2000 , name = "image_url")
    private String imageUrl;

    private double dollarPrice;
    private double rublePrice;

    public double getDollarPrice() {
        return dollarPrice;
    }

    public void setDollarPrice(double dollarPrice) {
        this.dollarPrice = dollarPrice;
    }

    public double getRublePrice() {
        return rublePrice;
    }

    public void setRublePrice(double rublePrice) {
        this.rublePrice = rublePrice;
    }

    @Column(unique = true, name="class_id")
    private String classId;


    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Override
    public UserList getOwners() {
        return null;
    }
}
