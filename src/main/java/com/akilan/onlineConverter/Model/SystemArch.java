package com.akilan.onlineConverter.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SystemArch {
    private String processors;
    private String availableRam;
    private String allocatedRam;
    private String totalRam;
    private String usableDiskVolume;
    private String totalDiskVolume;
}
