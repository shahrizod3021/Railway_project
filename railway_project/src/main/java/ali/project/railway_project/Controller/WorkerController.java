package ali.project.railway_project.Controller;

import ali.project.railway_project.Payload.ApiResponse;
import ali.project.railway_project.Payload.ReqWorker;
import ali.project.railway_project.Payload.ResWorker;
import ali.project.railway_project.Service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.json.HTTP;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/worker")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;


    @GetMapping
    public HttpEntity<?> getWorker(){
        List<ResWorker> workers = workerService.workers();
        return ResponseEntity.ok(workers);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOneWorker(@PathVariable UUID id){
        ResWorker oneWorker = workerService.getOneWorker(id);
        return ResponseEntity.ok(oneWorker);
    }


    @PostMapping
    public HttpEntity<?> addWorker(@RequestBody ReqWorker reqWorker){
        ApiResponse apiResponse = workerService.addWorker(reqWorker);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editWorker(@PathVariable UUID id, @RequestBody ReqWorker reqWorker){
        ApiResponse apiResponse = workerService.editWorker(id, reqWorker);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteWorker(@PathVariable UUID id){
        ApiResponse apiResponse = workerService.deleteWorker(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
