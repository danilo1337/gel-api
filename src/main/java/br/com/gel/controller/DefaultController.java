package br.com.gel.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class DefaultController {

    public static String CONTENT_RANGE_HEADER = "Content-Range";

    protected HttpHeaders getHttpHeaders(Object id) {
        URI location;

        if (id == null) {
            location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        } else {
            location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.LOCATION, location.toString());
        return responseHeaders;
    }

    protected HttpHeaders getHttpHeaders(String path, Object... uriVariableValues) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(path).buildAndExpand(uriVariableValues)
                .toUri();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.LOCATION, location.toString());
        return responseHeaders;
    }

    protected <T> ResponseEntity<Page<T>> getPagedResponse(Page<T> page, Integer pageNumber, Integer pageSize) {
        ResponseHeaderPaginable paginable = new ResponseHeaderPaginable(pageNumber, page);
        paginable.invoke();
        HttpStatus status = paginable.getStatus();
        return ResponseEntity.status(status).header(CONTENT_RANGE_HEADER, paginable.responsePageRange()).body(page);
    }

    class ResponseHeaderPaginable {
        private Integer page;
        private Page<?> list;
        private Integer total;
        private Integer offset;
        private HttpStatus status;

        public ResponseHeaderPaginable(Integer page, Page<?> list) {
            this.page = page;
            this.list = list;
        }

        public Integer getTotal() {
            return total;
        }

        public Integer getOffset() {
            return offset;
        }

        public HttpStatus getStatus() {
            return status;
        }

        private HttpStatus readStatus(Page<?> list, Integer total, Integer offset) {
            return list.getSize() == 0 ? HttpStatus.NO_CONTENT
                    : ((list.getSize() + offset < total ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK));
        }

        protected ResponseHeaderPaginable invoke() {
            total = Math.toIntExact(list.getTotalElements());
            offset = Optional.ofNullable(page).orElse(0);
            status = readStatus(list, total, offset);
            return this;
        }

        protected String responsePageRange() {
            return offset + "-" + offset + list.getSize() + "/" + total;
        }
    }

    class ResponseHeader {

        private HttpStatus status;
        private List<?> list;

        public ResponseHeader(List<?> list) {
            this.list = list;
        }

        public HttpStatus getStatus() {
            return status;
        }

        private HttpStatus readStatus(List<?> list) {
            return list.size() == 0 ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        }

        protected ResponseHeader invoke() {
            status = readStatus(list);
            return this;
        }
    }
}
