package com.demo.t_web.program.comn.model

data class DrivingVo(
    var code : Int,
    var message : String,
    var currentDateTime : String,
    var route : RouteData?,
    var duration : Double,
    var durationMin : Int
) {

    companion object {
        data class RouteData (
            var traoptimal : List<TraoptimalData>
        ){
            companion object {
                data class TraoptimalData (
                    var guide : List<GuideData>,
                    var path : List<Array<Double>>,
                    var section : List<SectionData>,
                    var summary : SummaryData,
                ) {
                    companion object {

                        data class GuideData (
                            var pointIndex : Int,
                            var type : Int,
                            var instructions : String,
                            var distance : Int,
                            var duration : Int
                        )

                        data class SectionData (
                            var pointIndex : Int,
                            var pointCount : Int,
                            var distance : Int,
                            var name : String,
                            var congestion : Int,
                            var speed : Int
                        )

                        data class SummaryData (
                            var start : StartLocation,
                            var goal : GoalLocation,
                            var distance : Int,
                            var duration : Int,
                            var bbox: Array<DoubleArray>,
                            var tollFare : Int,
                            var taxiFare : Int,
                            var fuelPrice : Int,
                            var departureTime : String,
                            var etaServiceType : Int
                        ) {
                            companion object {
                                data class GoalLocation(
                                    var location : List<Double>,
                                    var dir : Int
                                )

                                data class StartLocation(
                                    var location : List<Double>
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}