
package com.app.policebandobastapi.service;

import com.app.policebandobastapi.dto.ForceRequestDto;
import com.app.policebandobastapi.model.ForceRequest;

import java.util.List;

public interface ForceRequestService {

    // Create a force request for a Bandobast
    public ForceRequest createForceRequest(
            Long bandobastId,
            ForceRequestDto forceRequestDto
    );

    // Get all force requests for a Bandobast
    public List<ForceRequest> getAllForceRequests(Long bandobastId);

    // Get a force request by ID
    public ForceRequest findById(Long bandobastId, Long requestId);

    // Update an existing force request
    public ForceRequest updateForceRequest(
            Long bandobastId,
            Long requestId,
            ForceRequestDto forceRequestDto
    );

    // Delete a force request
    public void deleteForceRequest(Long bandobastId, Long requestId);
}
