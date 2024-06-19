package org.capaub.sprint_chatjs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChartJsService {

    public String convertListMapToJson(List<Map<String, Object>> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
