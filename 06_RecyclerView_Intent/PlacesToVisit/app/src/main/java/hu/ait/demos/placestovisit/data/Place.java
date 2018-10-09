package hu.ait.demos.placestovisit.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import hu.ait.demos.placestovisit.R;

@Entity
public class Place implements Serializable {
    public enum PlaceType {
        LANDSCAPE(0, R.drawable.landscape),
        CITY(1, R.drawable.city), BUILDING(2, R.drawable.building);

        private int value;
        private int iconId;

        private PlaceType(int value, int iconId) {
            this.value = value;
            this.iconId = iconId;
        }

        public int getValue() {
            return value;
        }

        public int getIconId() {
            return iconId;
        }

        public static PlaceType fromInt(int value) {
            for (PlaceType p : PlaceType.values()) {
                if (p.value == value) {
                    return p;
                }
            }
            return LANDSCAPE;
        }
    }

    @PrimaryKey(autoGenerate = true)
    private long placeID;

    private String placeName;
    private String description;
    private long pickUpDate;
    private int placeType;

    public Place(String placeName, String description, long pickUpDate, int placeType) {
        this.placeName = placeName;
        this.description = description;
        this.pickUpDate = pickUpDate;
        this.placeType = placeType;
    }

    public long getPlaceID() {
        return placeID;
    }

    public void setPlaceID(long placeID) {
        this.placeID = placeID;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(long pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public int getPlaceType() {
        return placeType;
    }

    public void setPlaceType(int placeType) {
        this.placeType = placeType;
    }

    public PlaceType getPlaceTypeAsEnum() {
        return PlaceType.fromInt(placeType);
    }

}
