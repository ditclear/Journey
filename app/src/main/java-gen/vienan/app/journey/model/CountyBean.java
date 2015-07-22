package vienan.app.journey.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table county.
 */
public class CountyBean {

    private Long id;
    private String county_name;
    private String county_code;
    private Integer city_id;

    public CountyBean() {
    }

    public CountyBean(Long id) {
        this.id = id;
    }

    public CountyBean(Long id, String county_name, String county_code, Integer city_id) {
        this.id = id;
        this.county_name = county_name;
        this.county_code = county_code;
        this.city_id = city_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCounty_name() {
        return county_name;
    }

    public void setCounty_name(String county_name) {
        this.county_name = county_name;
    }

    public String getCounty_code() {
        return county_code;
    }

    public void setCounty_code(String county_code) {
        this.county_code = county_code;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

}