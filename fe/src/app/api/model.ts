export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

export interface MgmtEnvironmentResponse {
  id: number;
  name: string;
}

export interface MgmtServiceResponse {
  id: number;
  name: string;
  owner: string;
  environmentId: number;
}