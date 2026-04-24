package com.continuum.config

object AppConfig {
    const val SUPABASE_URL      = "https://YOUR_PROJECT.supabase.co"
    const val SUPABASE_ANON_KEY = "YOUR_ANON_KEY"
    const val ANTHROPIC_BASE_URL = "https://api.anthropic.com/v1"
    const val ANTHROPIC_MODEL    = "claude-sonnet-4-5"
    // Move to local.properties / BuildConfig in production — never commit the real key
    const val ANTHROPIC_API_KEY  = "YOUR_ANTHROPIC_KEY"
}
