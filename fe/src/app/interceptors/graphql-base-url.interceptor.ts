import { HttpInterceptorFn } from '@angular/common/http';

import { environment } from '../../environments/environment';

export const graphqlBaseUrlInterceptor: HttpInterceptorFn = (req, next) =>
  next(req.clone({ url: `${environment.graphqlBaseUrl}${req.url}` }));