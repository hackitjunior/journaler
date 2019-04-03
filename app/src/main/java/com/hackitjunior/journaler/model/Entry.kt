package com.hackitjunior.journaler.model

import android.location.Location
import com.hackitjunior.journaler.database.DbModel

abstract class Entry(
    var title: String,
    var message: String,
    var location: Location
) : DbModel()