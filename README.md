# Fio Caching Server

[![GitHub](https://img.shields.io/github/license/chase22/fio-cache-server?logo=GPL-3.0)](https://github.com/Chase22/fio-cache-server)
[![GitHub issues](https://img.shields.io/github/issues/chase22/fio-cache-server)](https://github.com/Chase22/fio-cache-server/issues)
[![Docker Image Version (latest semver)](https://img.shields.io/docker/v/chase22/fio-cache?label=docker)](https://hub.docker.com/r/chase22/fio-cache)

A simple proxy for https://rest.fnar.net

## Features

- Collection of historical data from fio
- Caching of fio requests to speed up large requests

## How to use

### Cached requests

Send any request to the server using the same path as would be used in FIO.

For Example: `localhost/recipes/BBH` maps to `rest.fnar.net/recipes/BBH`

Additionally, the `maxAge` query parameter can be set using
the [java duration format](https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html#parse-java.lang.CharSequence-) `PnDTnHnM.nS`
. This defaults to 10 Minutes. The newest cache-hit will be returned that is not older than `maxAge`. Larger Durations
will increase the likeliness of a cache hit and therefore improve performance

#### Additional Headers

The following headers will be set on any cached request

| Header          | Example                     | Description                                                                                                                                            |
|-----------------|-----------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| X-Fio-Cache-Hit | true                        | If a cache item was found. If false a request to fio was send                                                                                          |
| X-Fio-Cache-Age | 2022-05-14T07:56:54.538877Z | The timestamp when this item was last requested from fio. If there was no cache hit, this will be the current timestamp. Will always be UTC(Zulu Time) |

### Historical data

Prefixing a request with /history/ returns all saved historical data for a given path in the format

```json
[
  {
    "timestamp": "2022-05-12T10:11:12.440+00:00",
    "data": "Some value returned by fio"
  },
  {
    "...": "..."
  }
]
```

## Currently unsupported

### Authorized Requests

Authorization needs to be implemented first. All requests to fio are unauthorized at the moment and will return a 401 if
an authorization is required

### User Permissions

Endpoints that require user specific visibility (e.g requesting Storage information for a different user) requires
special implementation to not leak information outside of the given permissions

### Admin endpoints

All /admin endpoints are blacklisted

### Post Requests

This server is purely for pulling data. Only GET Requests are supported

## Planned Features

### Bulk requests

When requesting data for a large amount of parameters that is currently not supported by fio (e.G.
/company/name/{CompanyName} for a list of company names) the list of parameters can be passed to the server. For each
parameter the cache will be requested an if not available will be fetched from fio. Large maxAges can drastically
increase performance

### CSV Transformations

When requesting bulk-data from a JSON endpoint, a pattern can be passed to transform the data into a csv for easier
import into e.G. google sheets

### Automatic historical data

Certain public endpoints (e.g Price data) will be automatically requested and saved so that historical data will be
available for these endpoints