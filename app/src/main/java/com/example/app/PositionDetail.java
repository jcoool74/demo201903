package com.example.app;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
  {
    "id": "bc5f25f3-1545-4c9d-baf6-ecbf402305f6",
    "type": "Full Time",
    "url": "https://jobs.github.com/positions/bc5f25f3-1545-4c9d-baf6-ecbf402305f6",
    "created_at": "Wed Mar 06 19:07:32 UTC 2019",
    "company": "Internet Archive",
    "company_url": null,
    "location": "San Francisco",
    "title": "Software Engineer",
    "description": "\u003cp\u003eThe Internet Archive is looking for an expert software engineer to join the Front-End UX Team.\u003c/p\u003e\n\u003cp\u003eYou will be a core developer on Archive.org (a Top 250 website), responsible for ideating and implementing new site features in collaboration with others, as well as maintaining the health and efficiency of the existing site. This is a rare opportunity to become a critical member of a small team making a huge impact in the world, and as part of the Internet Archive, you'll be joining a diverse group of informed, creative, engaging, wickedly smart individuals.\u003c/p\u003e\n\u003cp\u003eAt the Internet Archive, we believe that access to knowledge is a fundamental human right. We are building a digital library of everything, which anyone can upload to for free. We provide free access to researchers, historians, scholars, and the general public. In the Wayback Machine, we've saved over 330 billion web pages. We protect our users' privacy and provide special access to books for the print-disabled. A million people visit Archive.org every day.\u003c/p\u003e\n\u003cp\u003eOur headquarters are located in San Francisco, and there we host public forums, art exhibitions, performances, film screenings, and other community events. However, our 150+ employees span the globe.\u003c/p\u003e\n\u003cp\u003eResponsibilities:\u003c/p\u003e\n\u003cp\u003eDeveloping and maintaining the Archive.org website\u003c/p\u003e\n\u003cp\u003eImplementing new user-facing features in the main codebase as well as via our front-end component platform\u003c/p\u003e\n\u003cp\u003eMaintaining and evolving select back-end API endpoints and middleware\u003c/p\u003e\n\u003cp\u003eIntegrating with external services, such as payments, mailing, and CRM software\u003c/p\u003e\n\u003cp\u003eWriting and delivering high-quality software along with automated tests\u003c/p\u003e\n\u003cp\u003eCollaborating with stakeholders and designers to develop new site features\u003c/p\u003e\n\u003cp\u003eParticipating in regular code reviews and software planning/retrospectives\u003c/p\u003e\n\u003cp\u003eResponding to internal organization and external partner site needs\u003c/p\u003e\n\u003cp\u003eReducing technical debt\u003c/p\u003e\n\u003cp\u003eBeing a role model for effective and collaborative engineering practices\u003c/p\u003e\n\u003cp\u003eRequirements:\u003c/p\u003e\n\u003cp\u003e3+ years of relevant work experience in a collaborative software development environment\u003c/p\u003e\n\u003cp\u003eExcellent full-stack coding skills (PHP, semantic HTML, ES6 Javascript, well-structured CSS/LESS, some Python)\u003c/p\u003e\n\u003cp\u003ePassionate about web standards, front-end technologies, code quality\u003c/p\u003e\n\u003cp\u003eExpertise in at least 1 front-end \"framework\" (e.g. React, Vue, etc.)\u003c/p\u003e\n\u003cp\u003eSolid OOP skills with awareness of Functional Programming patterns\u003c/p\u003e\n\u003cp\u003eStrong background in automated testing and test-driven design (TDD)\u003c/p\u003e\n\u003cp\u003eExcellent problem-solving and debugging skills\u003c/p\u003e\n\u003cp\u003eExperience implementing mobile responsive designs\u003c/p\u003e\n\u003cp\u003eExcellent verbal and written communication skills\u003c/p\u003e\n\u003cp\u003eComfort working in a loosely structured environment requiring autonomy and initiative\u003c/p\u003e\n\u003cp\u003eWillingness to learn and change, reach compromise with others\u003c/p\u003e\n\u003cp\u003eRemote work with periodic on-sites possible, depending on candidate\u003c/p\u003e\n\u003cp\u003ePreferred Skills:\u003c/p\u003e\n\u003cp\u003eProduct design and development experience\u003c/p\u003e\n\u003cp\u003eInterest in user experience (UX) design\u003c/p\u003e\n\u003cp\u003eFront-end bundling/deploy technologies (Webpack, Babel)\u003c/p\u003e\n\u003cp\u003eAccessibility (WCAG, WAI-ARIA, A11Y, WebAIM, etc.)\u003c/p\u003e\n\u003cp\u003eDocker/Kubernetes containerization\u003c/p\u003e\n\u003cp\u003eDecentralized web\u003c/p\u003e\n\u003cp\u003eCiviCRM\u003c/p\u003e\n\u003cp\u003eStripe, Paypal, cryptocurrency platforms\u003c/p\u003e\n\u003cp\u003eGit, GitLab\u003c/p\u003e\n\u003cp\u003eJIRA, Kanban\u003c/p\u003e\n\u003cp\u003eBenefits \u0026amp; Perks:\u003c/p\u003e\n\u003cp\u003eThe Internet Archive provides a comprehensive benefits package including: PTO, paid holidays, medical, dental, vision, FSA, commuter, STD, LTD, 403B/Roth accounts. Work-life balance is important to us. For engineers working at HQ, we offer catered Friday lunches and work-at-home Wednesdays.\u003c/p\u003e\n\u003cp\u003eThe Internet Archive is an Equal Opportunity Employer M/F/D/V/L/G/B/T and will consider for employment, qualified applicants with criminal histories in a manner consistent with the requirements of the Fair Chance Ordinance.\u003c/p\u003e\n",
    "how_to_apply": "\u003cp\u003eapply at archive.org/jobs\u003c/p\u003e\n",
    "company_logo": "https://jobs.github.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBbkpnIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--8efb23ea6701e476ceaf7ed4d86a61f96767d468/internet-archive-logo-white.jpg"
  },
 */

public class PositionDetail extends BaseObservable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("company_url")
    @Expose
    private String companyUrl;
    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("how_to_apply")
    @Expose
    private String howToApply;

    @SerializedName("company_logo")
    @Expose
    private String companyLogo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Bindable
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
        notifyPropertyChanged(BR.company);
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHowToApply() {
        return howToApply;
    }

    public void setHowToApply(String howToApply) {
        this.howToApply = howToApply;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

}