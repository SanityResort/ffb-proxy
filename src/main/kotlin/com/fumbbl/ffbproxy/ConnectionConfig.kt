package com.fumbbl.ffbproxy

import com.fumbbl.ffbproxy.ffb.FfbConnection

data class ConnectionConfig(val primary: FfbConnection, val available: List<FfbConnection>, val running: List<FfbConnection>)