package ru.tinkoff.academy.job;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {
    private final JobServiceFacade jobServiceFacade;

    @PostMapping("/{id}/request")
    public Job acceptJobRequest(@PathVariable("id") String workerId, @RequestParam("jobRequest") String jobRequestId) {
        return jobServiceFacade.acceptJobRequest(jobRequestId, workerId);
    }

    @GetMapping("/{id}/request")
    public List<Job> findAllWorkerRequests(@PathVariable("id") String workerId) {
        return jobServiceFacade.findAllWorkerRequests(workerId);
    }

    @PutMapping("/{id}/done")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void finishJob(@PathVariable("id") String workerId, @RequestParam("job") String jobId) {
        jobServiceFacade.finishJob(jobId);
    }

    @DeleteMapping("/{id}/request")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void rejectJobRequest(@PathVariable("id") String workerId, @RequestParam("jobRequest") String jobRequestId) {
        jobServiceFacade.rejectJobRequest(workerId, jobRequestId);
    }
}
