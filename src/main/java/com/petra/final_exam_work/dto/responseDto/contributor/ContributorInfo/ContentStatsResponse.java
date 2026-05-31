package com.petra.final_exam_work.dto.responseDto.contributor.ContributorInfo;
import com.petra.final_exam_work.entity.enums.ContentType;

public class ContentStatsResponse {

    private ContentType type;

    private Integer total;
    private Integer published;
    private Integer draft;
    private Integer archived;
    private Integer scheduled;

    public ContentStatsResponse() {
    }

    public ContentStatsResponse(ContentType type, Integer total, Integer published, Integer draft, Integer archived,
                                Integer scheduled) {
        this.type = type;
        this.total = total;
        this.published = published;
        this.draft = draft;
        this.archived = archived;
        this.scheduled = scheduled;
    }

    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
        this.type = type;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPublished() {
        return published;
    }

    public void setPublished(Integer published) {
        this.published = published;
    }

    public Integer getDraft() {
        return draft;
    }

    public void setDraft(Integer draft) {
        this.draft = draft;
    }

    public Integer getArchived() {
        return archived;
    }

    public void setArchived(Integer archived) {
        this.archived = archived;
    }

    public Integer getScheduled() {
        return scheduled;
    }

    public void setScheduled(Integer scheduled) {
        this.scheduled = scheduled;
    }
}
