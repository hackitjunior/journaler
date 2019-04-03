package com.hackitjunior.journaler.permission

interface PermissionRequestCallback {
    fun onPermissionGranted(permissions: List<String>)
    fun onPermissionDenied(permissions: List<String>)
}