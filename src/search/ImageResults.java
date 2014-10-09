package search;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class ImageResults {
	private ResponseData responseData;
    private Object responseDetails;
    private Integer responseStatus;

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public Object getResponseDetails() {
        return responseDetails;
    }

    public void setResponseDetails(Object responseDetails) {
        this.responseDetails = responseDetails;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }

}

