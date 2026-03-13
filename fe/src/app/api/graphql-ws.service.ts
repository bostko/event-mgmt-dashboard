import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';

interface GqlWsMessage {
  id?: string;
  type: string;
  payload?: unknown;
}

@Injectable({ providedIn: 'root' })
export class GraphqlWsService {
  private readonly wsUrl = environment.graphqlBaseUrl.replace(/^http/, 'ws') + '/graphql';

  subscribe<T>(query: string): Observable<T> {
    return new Observable(observer => {
      const ws = new WebSocket(this.wsUrl, 'graphql-transport-ws');
      const id = crypto.randomUUID();

      ws.onopen = () => ws.send(JSON.stringify({ type: 'connection_init', payload: {} }));

      ws.onmessage = ({ data }) => {
        const msg: GqlWsMessage = JSON.parse(data);
        switch (msg.type) {
          case 'connection_ack':
            ws.send(JSON.stringify({ id, type: 'subscribe', payload: { query } }));
            break;
          case 'next':
            if (msg.id === id) observer.next((msg.payload as { data: T }).data);
            break;
          case 'error':
            observer.error(msg.payload);
            break;
          case 'complete':
            observer.complete();
            break;
        }
      };

      ws.onerror = err => observer.error(err);
      ws.onclose = () => observer.complete();

      return () => {
        if (ws.readyState === WebSocket.OPEN) {
          ws.send(JSON.stringify({ id, type: 'complete' }));
          ws.close();
        }
      };
    });
  }
}