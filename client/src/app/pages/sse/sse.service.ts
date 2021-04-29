import { Injectable, NgZone } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: "root"
})

export class SseService {

    constructor(private _zone: NgZone) {}

    getServerSentEvent(url: string): Observable<any> {

        return Observable.create(observer:any => {
            const eventSource = this.getEventSource(url);

            eventSource.onmessage = event => {
                this._zone.run(() => {
                    observer.next(event);
                });
            };

            eventSource.onmessage = error => {
                this._zone.run(() => {
                    observer.error(error);
                });
            }
        })
    }

    getEventSource(url: string): EventSource {
        return new EventSource(url);
    }
}