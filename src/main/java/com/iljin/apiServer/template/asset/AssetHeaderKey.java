package com.iljin.apiServer.template.asset;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AssetHeaderKey implements Serializable {
    String siteId;
    String assetId;
}
