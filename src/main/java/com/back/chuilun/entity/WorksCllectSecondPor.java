package com.back.chuilun.entity;

public class WorksCllectSecondPor extends SecondPor {


    private Long portfolioId;

    private String portfolioName;

    private Integer pstatus;

    private Long pcreateTime;

    private Long pupdateTime;

    private String poreditor;

    private String portfolioUpdateman;

    private Integer portfolioSpare;

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    @Override
    public String getPortfolioName() {
        return portfolioName;
    }

    @Override
    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public Integer getPstatus() {
        return pstatus;
    }

    public void setPstatus(Integer pstatus) {
        this.pstatus = pstatus;
    }

    public Long getPcreateTime() {
        return pcreateTime;
    }

    public void setPcreateTime(Long pcreateTime) {
        this.pcreateTime = pcreateTime;
    }

    public Long getPupdateTime() {
        return pupdateTime;
    }

    public void setPupdateTime(Long pupdateTime) {
        this.pupdateTime = pupdateTime;
    }

    public String getPoreditor() {
        return poreditor;
    }

    public void setPoreditor(String poreditor) {
        this.poreditor = poreditor;
    }

    public String getPortfolioUpdateman() {
        return portfolioUpdateman;
    }

    public void setPortfolioUpdateman(String portfolioUpdateman) {
        this.portfolioUpdateman = portfolioUpdateman;
    }

    public Integer getPortfolioSpare() {
        return portfolioSpare;
    }

    public void setPortfolioSpare(Integer portfolioSpare) {
        this.portfolioSpare = portfolioSpare;
    }
}
