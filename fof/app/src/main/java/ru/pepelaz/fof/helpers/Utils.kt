package ru.pepelaz.fof.helpers

import android.location.Address

object Utils {

    fun constructLocationName(a: Address): String {
        var result = ""
        if (a.thoroughfare != null) {
            result += a.thoroughfare
        }
        if (a.subThoroughfare != null) {
            result += ", " + a.subThoroughfare
        }

        if (a.locality != null) {
            result += ", " + a.locality
        }

        if (a.countryName != null) {
            result += ", " + a.countryName
        }
        return result;
    }
}