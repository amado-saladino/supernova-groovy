package models

import groovy.transform.Canonical

@Canonical
class UserReqRes {
    int id
    String email
    String first_name
    String last_name
    String avatar
}
