package com.test.demo.controller;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.IDCardOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.IDCardOCRResponse;
import com.tencentcloudapi.tia.v20180226.models.Model;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static com.test.demo.util.GetBase64FromInputStream.getBase64FromInputStream;


/**
 * orc识别
 */
@Controller
@CrossOrigin
@RequestMapping("/ocr")
public class OrcController {

    @RequestMapping("/swd")
    @ResponseBody
    public String swd(){
        return "swd";
    }

    @PostMapping("uploadFile")
    @ResponseBody
    public IDCardOCRResponse OCRIdCardTest(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "card_side") String cardSize, Model model){
        try {
            Credential cred = new Credential("AKIDtfMxNd2tHtQnohIlvMfqqe8SQM9shn3H", "DbzBD4kEj3d2KPVQ9q12yiO4avBlAOQx");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("ocr.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            OcrClient client = new OcrClient(cred, "ap-beijing", clientProfile);
            Map<String, String> params = new HashMap<>();
            params.put("ImageBase64", getBase64FromInputStream(file.getInputStream()));
            params.put("CardSide", cardSize);

            System.out.println(getBase64FromInputStream(file.getInputStream()));
            IDCardOCRRequest req = IDCardOCRRequest.fromJsonString(JSONObject.fromObject(params).toString(), IDCardOCRRequest.class);
            IDCardOCRResponse resp = client.IDCardOCR(req);
            return resp;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;

    }

}
