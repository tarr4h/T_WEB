package com.demo.t_web.program.comn.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <pre>
 * com.web.trv.comn.model.DrivingVo
 *   - DrivingVo.java
 * </pre>
 *
 * @author : 한태우
 * @ClassName : DrivingVo
 * @description :
 * @date : 2023/07/31
 */
@Getter
@Setter
public class DrivingVo {
    private int code;
    private String message;
    private String currentDateTime;
    private RouteData route;

    private double duration;
    private int durationMin;

    @Getter
    @Setter
    public static class RouteData{
        private List<TraoptimalData> traoptimal;

        @Getter
        @Setter
        public static class TraoptimalData {
            private List<GuideData> guide;
            private List<double[]> path;
            private List<SectionData> section;
            private SummaryData summary;

            @Getter
            @Setter
            public static class GuideData {
                private int pointIndex;
                private int type;
                private String instructions;
                private int distance;
                private int duration;
            }

            @Getter
            @Setter
            public static class SectionData {
                private int pointIndex;
                private int pointCount;
                private int distance;
                private String name;
                private int congestion;
                private int speed;
            }

            @Getter
            @Setter
            public static class SummaryData {
                private StartLocation start;
                private GoalLocation goal;
                private int distance;
                private int duration;
                private double[][] bbox;
                private int tollFare;
                private int taxiFare;
                private int fuelPrice;
                private String departureTime;
                private int etaServiceType;

                @Getter
                @Setter
                public static class GoalLocation {
                    private double[] location;
                    private int dir;
                }

                @Getter
                @Setter
                public static class StartLocation {
                    private double[] location;
                }
            }
        }
    }


}