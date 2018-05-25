package ru.pepelaz.fof.helpers

import android.location.Address

object Utils {

    fun constructLocationName(a: Address): String {
        var result = ""
        for (i in 0..a.maxAddressLineIndex) {
            if (result != "") result += ", "
            result += a.getAddressLine(i)
        }
       /* if (a.thoroughfare != null) {
            result += a.thoroughfare
        }
        if (a.subThoroughfare != null) {
            if (result != "") result += ", "
            result += a.subThoroughfare
        }

        if (a.locality != null) {
            if (result != "") result += ", "
            result += a.locality
        }

        if (a.countryName != null) {
            if (result != "") result += ", "
            result += a.countryName
        }*/
        return result
    }
}