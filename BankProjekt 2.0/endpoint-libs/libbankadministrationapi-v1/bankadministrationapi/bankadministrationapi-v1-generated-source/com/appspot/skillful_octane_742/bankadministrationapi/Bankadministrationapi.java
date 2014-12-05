/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-11-17 18:43:33 UTC)
 * on 2014-12-02 at 18:19:03 UTC 
 * Modify at your own risk.
 */

package com.appspot.skillful_octane_742.bankadministrationapi;

/**
 * Service definition for Bankadministrationapi (v1).
 *
 * <p>
 * Bank Administration APIs
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link BankadministrationapiRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Bankadministrationapi extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the bankadministrationapi library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://skillful-octane-742.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "bankadministrationapi/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Bankadministrationapi(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Bankadministrationapi(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getAccountById".
   *
   * This request holds the parameters needed by the bankadministrationapi server.  After setting any
   * optional parameters, call the {@link GetAccountById#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetAccountById getAccountById(java.lang.Integer id) throws java.io.IOException {
    GetAccountById result = new GetAccountById(id);
    initialize(result);
    return result;
  }

  public class GetAccountById extends BankadministrationapiRequest<com.appspot.skillful_octane_742.bankadministrationapi.model.Account> {

    private static final String REST_PATH = "account/{id}";

    /**
     * Create a request for the method "getAccountById".
     *
     * This request holds the parameters needed by the the bankadministrationapi server.  After
     * setting any optional parameters, call the {@link GetAccountById#execute()} method to invoke the
     * remote operation. <p> {@link GetAccountById#initialize(com.google.api.client.googleapis.service
     * s.AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetAccountById(java.lang.Integer id) {
      super(Bankadministrationapi.this, "GET", REST_PATH, null, com.appspot.skillful_octane_742.bankadministrationapi.model.Account.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetAccountById setAlt(java.lang.String alt) {
      return (GetAccountById) super.setAlt(alt);
    }

    @Override
    public GetAccountById setFields(java.lang.String fields) {
      return (GetAccountById) super.setFields(fields);
    }

    @Override
    public GetAccountById setKey(java.lang.String key) {
      return (GetAccountById) super.setKey(key);
    }

    @Override
    public GetAccountById setOauthToken(java.lang.String oauthToken) {
      return (GetAccountById) super.setOauthToken(oauthToken);
    }

    @Override
    public GetAccountById setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetAccountById) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetAccountById setQuotaUser(java.lang.String quotaUser) {
      return (GetAccountById) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetAccountById setUserIp(java.lang.String userIp) {
      return (GetAccountById) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Integer id;

    /**

     */
    public java.lang.Integer getId() {
      return id;
    }

    public GetAccountById setId(java.lang.Integer id) {
      this.id = id;
      return this;
    }

    @Override
    public GetAccountById set(String parameterName, Object value) {
      return (GetAccountById) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getAllCustomers".
   *
   * This request holds the parameters needed by the bankadministrationapi server.  After setting any
   * optional parameters, call the {@link GetAllCustomers#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public GetAllCustomers getAllCustomers() throws java.io.IOException {
    GetAllCustomers result = new GetAllCustomers();
    initialize(result);
    return result;
  }

  public class GetAllCustomers extends BankadministrationapiRequest<com.appspot.skillful_octane_742.bankadministrationapi.model.CustomerCollection> {

    private static final String REST_PATH = "customercollection";

    /**
     * Create a request for the method "getAllCustomers".
     *
     * This request holds the parameters needed by the the bankadministrationapi server.  After
     * setting any optional parameters, call the {@link GetAllCustomers#execute()} method to invoke
     * the remote operation. <p> {@link GetAllCustomers#initialize(com.google.api.client.googleapis.se
     * rvices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected GetAllCustomers() {
      super(Bankadministrationapi.this, "GET", REST_PATH, null, com.appspot.skillful_octane_742.bankadministrationapi.model.CustomerCollection.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetAllCustomers setAlt(java.lang.String alt) {
      return (GetAllCustomers) super.setAlt(alt);
    }

    @Override
    public GetAllCustomers setFields(java.lang.String fields) {
      return (GetAllCustomers) super.setFields(fields);
    }

    @Override
    public GetAllCustomers setKey(java.lang.String key) {
      return (GetAllCustomers) super.setKey(key);
    }

    @Override
    public GetAllCustomers setOauthToken(java.lang.String oauthToken) {
      return (GetAllCustomers) super.setOauthToken(oauthToken);
    }

    @Override
    public GetAllCustomers setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetAllCustomers) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetAllCustomers setQuotaUser(java.lang.String quotaUser) {
      return (GetAllCustomers) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetAllCustomers setUserIp(java.lang.String userIp) {
      return (GetAllCustomers) super.setUserIp(userIp);
    }

    @Override
    public GetAllCustomers set(String parameterName, Object value) {
      return (GetAllCustomers) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Bankadministrationapi}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Bankadministrationapi}. */
    @Override
    public Bankadministrationapi build() {
      return new Bankadministrationapi(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link BankadministrationapiRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setBankadministrationapiRequestInitializer(
        BankadministrationapiRequestInitializer bankadministrationapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(bankadministrationapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
