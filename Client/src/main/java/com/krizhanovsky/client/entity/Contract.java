package com.krizhanovsky.client.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.control.CheckBox;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Contract {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("number")
    private String number;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd" , timezone="UTC")
    @JsonProperty("dateOfCreation")
    private Date dateOfCreation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd" , timezone="UTC")
    @JsonProperty("upDate")
    private Date upDate;
    @JsonIgnore
    private CheckBox actual;

    public Date getDateOfCreation(){
        return new Date(dateOfCreation.getTime() + (1000 * 60 * 60 * 24));
    }

    public Date getUpDate(){
        return new Date(upDate.getTime() + (1000 * 60 * 60 * 24));
    }

    public CheckBox getActual() {
        actual = new CheckBox();
        actual.setDisable(true);
        int daysDiff = (int) TimeUnit.DAYS.convert(Math.abs(new Date().getTime() - upDate.getTime()), TimeUnit.MILLISECONDS);
        actual.setSelected(daysDiff <= 60);
        return actual;
    }
}