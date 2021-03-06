package com.yaratech.yaratube.data.model;

import android.media.Image;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Vah on 9/2/2018.
 */

public class ProfileResponse {
        @SerializedName("credit")
        @Expose
        private String credit;
        @SerializedName("magic_credit")
        @Expose
        private String magicCredit;
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("friends")
        @Expose
        private List<ProfileResponse> friends = null;
        @SerializedName("error")
        @Expose
        private String error;
        @SerializedName("nickname")
        @Expose
        private String nickname;
        @SerializedName("date_of_birth")
        @Expose
        private Date dateOfBirth;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("avatar")
        @Expose
        private Image avatar;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("email")
        @Expose
        private String email;
        public String getCredit() {
            return credit;
        }
        public void setCredit(String credit) {
            this.credit = credit;
        }
        public String getMagicCredit() {
            return magicCredit;
        }
        public void setMagicCredit(String magicCredit) {
            this.magicCredit = magicCredit;
        }
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public List<ProfileResponse> getFriends() {
            return friends;
        }
        public void setFriends(List<ProfileResponse> friends) {
            this.friends = friends;
        }
        public String getError() {
            return error;
        }
        public void setError(String error) {
            this.error = error;
        }
        public String getNickname() {
            return nickname;
        }
        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
        public Date getDateOfBirth() {
            return dateOfBirth;
        }
        public void setDateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }
        public String getGender() {
            return gender;
        }
        public void setGender(String gender) {
            this.gender = gender;
        }
        public Image getAvatar() {
            return avatar;
        }
        public void setAvatar(Image avatar) {
            this.avatar = avatar;
        }
        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
        public String getMobile() {
            return mobile;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getEmail() {
            return email;
        }
    }