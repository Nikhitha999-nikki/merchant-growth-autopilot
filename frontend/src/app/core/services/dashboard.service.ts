import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api';

  getOrders(): Observable<any> {
    return this.http.get(`${this.apiUrl}/analytics/orders`);
  }

  getOpportunities(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/opportunities`);
  }

  getRecommendations(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/recommendations`);
  }

  approveRecommendation(id: number): Observable<any> {
    return this.http.put(
      `${this.apiUrl}/recommendations/${id}/approve`,
      {}
    );
  }

  rejectRecommendation(id: number): Observable<any> {
    return this.http.put(
      `${this.apiUrl}/recommendations/${id}/reject`,
      {}
    );
  }

  checkGuardrail(id: number): Observable<any> {
    return this.http.get(
      `${this.apiUrl}/guardrails/check/${id}`
    );
  }

  executeAction(id: number): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/actions/execute/${id}`,
      {}
    );
  }

  getActions(): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.apiUrl}/actions`
    );
  }

  measureImpact(id: number): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/impact/measure/${id}`,
      {}
    );
  }

  getImpact(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/impact`);
  }

  runAgent(goal: string): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/agent/run`,
      { goal }
    );
  }
}
