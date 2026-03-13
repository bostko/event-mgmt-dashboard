import { createEnvironmentInjector, EnvironmentInjector, inject, Injectable, OnDestroy } from '@angular/core';
import { HttpClient, provideHttpClient, withInterceptors } from '@angular/common/http';

import { graphqlBaseUrlInterceptor } from '../interceptors/graphql-base-url.interceptor';

@Injectable({ providedIn: 'root' })
export class GraphqlHttpClient implements OnDestroy {
  private readonly injector = createEnvironmentInjector(
    [provideHttpClient(withInterceptors([graphqlBaseUrlInterceptor]))],
    inject(EnvironmentInjector)
  );

  readonly http = this.injector.get(HttpClient);

  ngOnDestroy(): void {
    this.injector.destroy();
  }
}