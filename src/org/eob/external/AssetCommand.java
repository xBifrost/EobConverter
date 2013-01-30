package org.eob.external;

import java.util.Set;

/**
 * User: Bifrost
 * Date: 27.1.2013
 * Time: 20:22
 */
public interface AssetCommand {
    String getOriginalName();

    Set<String> getInternalNames();

    void setInternalNames(Set<String> internalNames);

    Long getAssetId();

    void setAssetId(Long assetId);
}
