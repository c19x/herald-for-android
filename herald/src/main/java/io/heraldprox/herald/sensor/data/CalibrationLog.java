//  Copyright 2021 Herald Project Contributors
//  SPDX-License-Identifier: Apache-2.0
//

package io.heraldprox.herald.sensor.data;

import android.content.Context;

import androidx.annotation.NonNull;

import io.heraldprox.herald.sensor.DefaultSensorDelegate;
import io.heraldprox.herald.sensor.datatype.InertiaLocationReference;
import io.heraldprox.herald.sensor.datatype.Location;
import io.heraldprox.herald.sensor.datatype.PayloadData;
import io.heraldprox.herald.sensor.datatype.Proximity;
import io.heraldprox.herald.sensor.datatype.SensorType;
import io.heraldprox.herald.sensor.datatype.TargetIdentifier;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/// CSV contact log for post event analysis and visualisation
public class CalibrationLog extends SensorDelegateLogger {

    public CalibrationLog(@NonNull final Context context, @NonNull final String filename) {
        super(context, filename);
    }

    private void writeHeader() {
        if (empty()) {
            write("time,payload,rssi,x,y,z");
        }
    }

    // MARK:- SensorDelegate

    @Override
    public void sensor(@NonNull final SensorType sensor, @NonNull final Proximity didMeasure, @NonNull final TargetIdentifier fromTarget, @NonNull final PayloadData withPayload) {
        writeHeader();
        write(timestamp() + "," + csv(withPayload.shortName()) + "," + didMeasure.value + ",,,");
    }

    @Override
    public void sensor(@NonNull final SensorType sensor, @NonNull final Location didVisit) {
        if (didVisit.value instanceof InertiaLocationReference) {
            final InertiaLocationReference reference = (InertiaLocationReference) didVisit.value;
            writeHeader();
            write(timestamp() + ",,," + reference.x + ","  + reference.y + "," + reference.z);
        }
    }
}
