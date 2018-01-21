package com.github.haschi.haushaltsbuch.backend.rest.api

import com.github.haschi.haushaltsbuch.backend.rest.model.Version
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class Version2Endpoint : VersionApiDelegate
{
    override fun versionGet(): ResponseEntity<Version>
    {
        val version = Version()
        version.buildId = "version-buildId"
        version.scmId = "version-scmId"

        return ResponseEntity(version, HttpStatus.OK)
    }
}