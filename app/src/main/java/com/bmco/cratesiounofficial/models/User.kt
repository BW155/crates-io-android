package com.bmco.cratesiounofficial.models

import java.util.HashMap
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "login", "email", "email_verified", "email_verification_sent", "name", "avatar", "url")
class User: Serializable {

    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: Int? = null
    @JsonProperty("login")
    @get:JsonProperty("login")
    @set:JsonProperty("login")
    var login: String? = null
    @JsonProperty("email")
    @get:JsonProperty("email")
    @set:JsonProperty("email")
    var email: String? = null
    @JsonProperty("email_verified")
    @get:JsonProperty("email_verified")
    @set:JsonProperty("email_verified")
    var emailVerified: Boolean? = null
    @JsonProperty("email_verification_sent")
    @get:JsonProperty("email_verification_sent")
    @set:JsonProperty("email_verification_sent")
    var emailVerificationSent: Boolean? = null
    @JsonProperty("name")
    @get:JsonProperty("name")
    @set:JsonProperty("name")
    var name: String? = null
    @JsonProperty("avatar")
    @get:JsonProperty("avatar")
    @set:JsonProperty("avatar")
    var avatar: String? = null
    @JsonProperty("url")
    @get:JsonProperty("url")
    @set:JsonProperty("url")
    var url: String? = null
    @JsonIgnore
    private val additionalProperties = HashMap<String, Any>()

    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, Any> {
        return this.additionalProperties
    }

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties[name] = value
    }

}