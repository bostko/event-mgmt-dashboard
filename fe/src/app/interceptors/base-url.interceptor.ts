import { HttpInterceptorFn } from '@angular/common/http';

import { environment } from '../../environments/environment';

export const baseUrlInterceptor: HttpInterceptorFn = (req, next) =>
  next(req.clone({ url: `${environment.apiBaseUrl}${req.url}` }));