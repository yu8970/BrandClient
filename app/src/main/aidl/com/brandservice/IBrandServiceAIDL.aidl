package com.brandservice;

import com.brandservice.BrandResult;
import com.brandservice.DetectorResult;
import com.brandservice.SearchResult;
import com.brandservice.DetectionTarget;

interface IBrandServiceAIDL {

    BrandResult getBrandResult(in ParcelFileDescriptor pfd);

}