package com.vacuum.app.cinema.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Home on 3/4/2018.
 */

public class Alluc {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("sourcetitle")
    @Expose
    private String sourcetitle;
    @SerializedName("imageid")
    @Expose
    private String imageid;
    @SerializedName("sourcename")
    @Expose
    private String sourcename;
    @SerializedName("sizeinternal")
    @Expose
    private Integer sizeinternal;
    @SerializedName("extension")
    @Expose
    private String extension;
    @SerializedName("metatags")
    @Expose
    private Boolean metatags;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("hostername")
    @Expose
    private String hostername;
    @SerializedName("hosterurls")
    @Expose
    private List<Hosterurl> hosterurls = null;
    @SerializedName("sourceurl")
    @Expose
    private String sourceurl;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("checked")
    @Expose
    private String checked;
    @SerializedName("stream")
    @Expose
    private Boolean stream;
    @SerializedName("download")
    @Expose
    private Boolean download;


    @SerializedName("thumbnail_base64")
    @Expose
    private String thumbnailBase64;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSourcetitle() {
        return sourcetitle;
    }

    public void setSourcetitle(String sourcetitle) {
        this.sourcetitle = sourcetitle;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public String getSourcename() {
        return sourcename;
    }

    public void setSourcename(String sourcename) {
        this.sourcename = sourcename;
    }

    public Integer getSizeinternal() {
        return sizeinternal;
    }

    public void setSizeinternal(Integer sizeinternal) {
        this.sizeinternal = sizeinternal;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Boolean getMetatags() {
        return metatags;
    }

    public void setMetatags(Boolean metatags) {
        this.metatags = metatags;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getHostername() {
        return hostername;
    }

    public void setHostername(String hostername) {
        this.hostername = hostername;
    }

    public List<Hosterurl> getHosterurls() {
        return hosterurls;
    }

    public void setHosterurls(List<Hosterurl> hosterurls) {
        this.hosterurls = hosterurls;
    }

    public String getSourceurl() {
        return sourceurl;
    }

    public void setSourceurl(String sourceurl) {
        this.sourceurl = sourceurl;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    public Boolean getDownload() {
        return download;
    }

    public void setDownload(Boolean download) {
        this.download = download;
    }

    public String getThumbnailBase64() {
        return thumbnailBase64;
    }

    public void setThumbnailBase64(String thumbnailBase64) {
        this.thumbnailBase64 = thumbnailBase64;
    }
}
