package ali.project.railway_project.Controller;

import ali.project.railway_project.Payload.ApiResponse;
import ali.project.railway_project.Payload.ReqClothe;
import ali.project.railway_project.Payload.ResClothe;
import ali.project.railway_project.Service.ClotheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clothe")
@RequiredArgsConstructor
public class ClotheController {

    private final ClotheService clotheService;

    @PostMapping("/{id}")
    public HttpEntity<?> addClothe(@PathVariable UUID id, @RequestBody ReqClothe reqClothe){
        ApiResponse apiResponse = clotheService.addClothe(id, reqClothe);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{uuid}/{id}")
    public HttpEntity<?> editClothe(@PathVariable UUID uuid, @PathVariable Integer id, @RequestBody ReqClothe reqClothe){
        ApiResponse apiResponse = clotheService.editClothe(uuid, id, reqClothe);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getClothe(@PathVariable UUID id){
        List<ResClothe> clothe = clotheService.getClothe(id);
        return ResponseEntity.ok(clothe);
    }

    @DeleteMapping("/{uuid}/{id}")
    public HttpEntity<?> deleteClothe(@PathVariable UUID uuid, @PathVariable  Integer id){
        ApiResponse apiResponse = clotheService.deleteClothe(uuid, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
