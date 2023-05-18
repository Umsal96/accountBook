package com.example.myapplication.item;

public class HomeListItem {
    private String linkUrl; // 공지사항 상세 URL
    private int page; // 페이지 정보
    private int rNum; // 순번
    private String bbsWouDttm; // 게시일
    private int allCnt; // 전체조회건수
    private Long bbsSn; // 번호
    private String bbsTl; // 제목
    private int inqCnt; // 조회수
    private String aisTpCdNm; // 유형
    private String ccrCnntSysDsCd; // 고객센터 연계시스템구분코드
    private String depNm; // 담당부서

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getrNum() {
        return rNum;
    }

    public void setrNum(int rNum) {
        this.rNum = rNum;
    }

    public String getBbsWouDttm() {
        return bbsWouDttm;
    }

    public void setBbsWouDttm(String bbsWouDttm) {
        this.bbsWouDttm = bbsWouDttm;
    }

    public int getAllCnt() {
        return allCnt;
    }

    public void setAllCnt(int allCnt) {
        this.allCnt = allCnt;
    }

    public Long getBbsSn() {
        return bbsSn;
    }

    public void setBbsSn(Long bbsSn) {
        this.bbsSn = bbsSn;
    }

    public String getBbsTl() {
        return bbsTl;
    }

    public void setBbsTl(String bbsTl) {
        this.bbsTl = bbsTl;
    }

    public int getInqCnt() {
        return inqCnt;
    }

    public void setInqCnt(int inqCnt) {
        this.inqCnt = inqCnt;
    }

    public String getAisTpCdNm() {
        return aisTpCdNm;
    }

    public void setAisTpCdNm(String aisTpCdNm) {
        this.aisTpCdNm = aisTpCdNm;
    }

    public String getCcrCnntSysDsCd() {
        return ccrCnntSysDsCd;
    }

    public void setCcrCnntSysDsCd(String ccrCnntSysDsCd) {
        this.ccrCnntSysDsCd = ccrCnntSysDsCd;
    }

    public String getDepNm() {
        return depNm;
    }

    public void setDepNm(String depNm) {
        this.depNm = depNm;
    }
}
