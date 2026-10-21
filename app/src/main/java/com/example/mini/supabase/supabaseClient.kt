package com.example.mini.supabase

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object supabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://fpturvxjvaphfxiopjlo.supabase.co",
        supabaseKey = "sb_publishable_Si1yFUrnslEnqR3fuKXtIQ__LrWTDLT"
    ) {
        install(Auth)
        install(Postgrest)
    }
}