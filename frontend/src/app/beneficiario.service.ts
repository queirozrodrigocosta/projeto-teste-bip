import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Beneficiario {
  id?: number;
  nome: string;
  cpf: string;
  saldo: number;
  version: number;
}

export interface Transferencia {
  id?: number;
  beneficiarioOrigem: Beneficiario;
  beneficiarioDestino: Beneficiario;
  valor: number;
}

export interface TransferenciaRequest {
  idOrigem: number;
  idDestino: number;
  valor: number;
}

@Injectable({
  providedIn: 'root'
})
export class BeneficiarioService {
  private apiUrl = 'http://localhost:8080/api/beneficiarios';

  constructor(private http: HttpClient) {}

  listar(): Observable<Beneficiario[]> {
    return this.http.get<Beneficiario[]>(this.apiUrl);
  }

  obter(id: number): Observable<Beneficiario> {
    return this.http.get<Beneficiario>(`${this.apiUrl}/${id}`);
  }

  criar(beneficiario: Beneficiario): Observable<Beneficiario> {
    return this.http.post<Beneficiario>(this.apiUrl, beneficiario);
  }

  atualizar(id: number, beneficiario: Beneficiario): Observable<Beneficiario> {
    return this.http.put<Beneficiario>(`${this.apiUrl}/${id}`, beneficiario);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  transferir(request: TransferenciaRequest): Observable<Transferencia> {
    return this.http.post<Transferencia>(`${this.apiUrl}/transferir`, request);
  }
}