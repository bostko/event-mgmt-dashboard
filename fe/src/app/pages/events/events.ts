import { Component, inject, OnDestroy, OnInit, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Subscription } from 'rxjs';

import { GraphqlHttpClient } from '../../api/graphql-http-client';
import { GraphqlWsService } from '../../api/graphql-ws.service';
import { MgmtEventResponse } from '../../api/model';

interface GraphqlResponse<T> {
  data: T;
}

const EVENTS_QUERY = '{ mgmtEvents { id description timestamp service { id name owner } } }';
const EVENT_SUBSCRIPTION = 'subscription { mgmtEventCreated { id description timestamp service { id name owner } } }';

@Component({
  selector: 'app-events',
  imports: [DatePipe],
  templateUrl: './events.html',
  styleUrl: './events.scss'
})
export class Events implements OnInit, OnDestroy {
  private readonly graphqlHttp = inject(GraphqlHttpClient);
  private readonly graphqlWs = inject(GraphqlWsService);
  private wsSub?: Subscription;

  readonly events = signal<MgmtEventResponse[] | null>(null);

  ngOnInit(): void {
    this.graphqlHttp.http
      .post<GraphqlResponse<{ mgmtEvents: MgmtEventResponse[] }>>('/graphql', { query: EVENTS_QUERY })
      .subscribe({ next: res => this.events.set(res.data.mgmtEvents) });

    this.wsSub = this.graphqlWs
      .subscribe<{ mgmtEventCreated: MgmtEventResponse }>(EVENT_SUBSCRIPTION)
      .subscribe({ next: data => this.events.update(prev => [data.mgmtEventCreated, ...(prev ?? [])]) });
  }

  ngOnDestroy(): void {
    this.wsSub?.unsubscribe();
  }
}