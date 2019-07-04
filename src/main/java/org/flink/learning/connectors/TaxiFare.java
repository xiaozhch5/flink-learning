/*
 * Copyright 2015 data Artisans GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.flink.learning.connectors;


import java.io.Serializable;

/**
 * A TaxiFare is a taxi fare event.
 *
 * A TaxiFare consists of
 * - the rideId of the event
 * - the time of the event
 *
 */
public class TaxiFare implements Serializable {



	public TaxiFare() {
//		this.startTime = new DateTime();
	}

	public TaxiFare(long rideId, long taxiId, long driverId, String startTime, String paymentType, float tip, float tolls, float totalFare) {

		this.rideId = rideId;
		this.taxiId = taxiId;
		this.driverId = driverId;
		this.startTime = startTime;
		this.paymentType = paymentType;
		this.tip = tip;
		this.tolls = tolls;
		this.totalFare = totalFare;
	}

	private long rideId;
	private long taxiId;
	private long driverId;
	private String startTime;
	private String paymentType;
	private float tip;
	private float tolls;
	private float totalFare;

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(rideId).append(",");
		sb.append(taxiId).append(",");
		sb.append(driverId).append(",");
//		sb.append(startTime.toString(timeFormatter)).append(",");
		sb.append(startTime).append(",");
		sb.append(paymentType).append(",");
		sb.append(tip).append(",");
		sb.append(tolls).append(",");
		sb.append(totalFare);

		return sb.toString();
	}

	public static TaxiFare fromString(String line) {

		String[] tokens = line.split(",");
		if (tokens.length != 8) {
			throw new RuntimeException("Invalid record: " + line);
		}

		TaxiFare ride = new TaxiFare();

		try {
			ride.rideId = Long.parseLong(tokens[0]);
			ride.taxiId = Long.parseLong(tokens[1]);
			ride.driverId = Long.parseLong(tokens[2]);
//			ride.startTime = DateTime.parse(tokens[3], timeFormatter);
			ride.startTime = tokens[3];
			ride.paymentType = tokens[4];
			ride.tip = tokens[5].length() > 0 ? Float.parseFloat(tokens[5]) : 0.0f;
			ride.tolls = tokens[6].length() > 0 ? Float.parseFloat(tokens[6]) : 0.0f;
			ride.totalFare = tokens[7].length() > 0 ? Float.parseFloat(tokens[7]) : 0.0f;

		} catch (NumberFormatException nfe) {
			throw new RuntimeException("Invalid record: " + line, nfe);
		}

		return ride;
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof TaxiFare &&
				this.rideId == ((TaxiFare) other).rideId;
	}

	@Override
	public int hashCode() {
		return (int)this.rideId;
	}

//	public long getEventTime() {
//		return startTime.getMillis();
//	}
}
