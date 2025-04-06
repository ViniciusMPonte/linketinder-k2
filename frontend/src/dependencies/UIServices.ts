import DOMQuery from "../view/DOMQuery"
import Notification from "../view/Notification"
import Redirect from "../view/Redirect"

interface UIServicesConfig {
    domQuery: DOMQuery
    notification: Notification
    redirect: Redirect
}

export default class UIServices {

    public readonly domQuery
    public readonly notification
    public readonly redirect

    constructor({
                    domQuery,
                    notification,
                    redirect
                }:UIServicesConfig) {
        this.domQuery = domQuery
        this.notification = notification
        this.redirect = redirect
    }
}