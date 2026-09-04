import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService } from '../../core/services/dashboard.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss'
})
export class Dashboard implements OnInit {

  private dashboardService = inject(DashboardService);

  totalOrders = 0;
  opportunities: any[] = [];
  recommendations: any[] = [];

  loading = true;

  activity: any[] = [];

  actionResult: any = null;
  impactResult: any = null;

  agentResult: any = null;
  agentRunning = false;

  executedRecommendationIds: number[] = [];

  ngOnInit(): void {
    this.loadDashboard();
  }

  loadDashboard(): void {

    this.dashboardService.getOrders().subscribe({
      next: (data) => {
        this.totalOrders = data.totalOrders;
      },
      error: (error) => {
        console.error('Orders API error:', error);
      }
    });

    this.dashboardService.getOpportunities().subscribe({
      next: (data) => {
        this.opportunities = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Opportunities API error:', error);
        this.loading = false;
      }
    });

    this.dashboardService.getRecommendations().subscribe({
      next: (data) => {
        this.recommendations = data;
      },
      error: (error) => {
        console.error('Recommendations API error:', error);
      }
    });
  }

  approveRecommendation(id: number): void {

    this.dashboardService.approveRecommendation(id).subscribe({
      next: () => {

        this.activity.unshift({
          message: 'Merchant approved recommendation',
          detail:
            'The recommendation is now eligible for guardrail validation.',
          status: 'APPROVED'
        });

        this.loadDashboard();
      },

      error: (error) => {
        console.error('Approval failed:', error);
      }
    });
  }

  rejectRecommendation(id: number): void {

    this.dashboardService.rejectRecommendation(id).subscribe({
      next: () => {

        this.activity.unshift({
          message: 'Merchant rejected recommendation',
          detail:
            'The proposed action was stopped by the merchant.',
          status: 'REJECTED'
        });

        this.loadDashboard();
      },

      error: (error) => {
        console.error('Rejection failed:', error);
      }
    });
  }

  checkAndExecute(recommendation: any): void {

    const id = recommendation.id;

    this.activity.unshift({
      message: 'Autopilot started',
      detail:
        'Checking policy guardrails before execution.',
      status: 'RUNNING'
    });

    this.dashboardService.checkGuardrail(id).subscribe({

      next: (guardrail) => {

        this.activity.unshift({
          message: 'Guardrail checked',
          detail: guardrail.reason,
          status: guardrail.decision
        });

        if (!guardrail.allowed) {

          this.actionResult = {
            status: 'BLOCKED',
            message: guardrail.reason
          };

          this.activity.unshift({
            message: 'Action blocked',
            detail: guardrail.reason,
            status: 'BLOCKED'
          });

          return;
        }

        this.activity.unshift({
          message: 'Guardrail passed',
          detail:
            'Policy validation succeeded. Action can be executed.',
          status: 'ALLOWED'
        });

        this.dashboardService.executeAction(id).subscribe({

          next: (execution) => {

            this.actionResult = execution;

            this.activity.unshift({
              message: 'Action executed',
              detail: execution.result,
              status: execution.status
            });

            /*
             * Track successfully executed recommendations.
             */
            if (execution.status === 'EXECUTED') {

              if (!this.executedRecommendationIds.includes(id)) {
                this.executedRecommendationIds.push(id);
              }

              /*
               * Automatically measure impact after successful execution.
               */
              this.dashboardService.measureImpact(id).subscribe({

                next: (impact) => {

                  this.impactResult = impact;

                  this.activity.unshift({
                    message: 'Impact measured',
                    detail:
                      `Conversion improved by ${impact.improvement.toFixed(2)} percentage points. ` +
                      `Estimated revenue impact: ₹${impact.estimatedRevenue.toFixed(2)}.`,
                    status: 'IMPACT_MEASURED'
                  });
                },

                error: (error) => {

                  console.error(
                    'Impact measurement failed:',
                    error
                  );

                  this.activity.unshift({
                    message: 'Impact measurement failed',
                    detail:
                      'The action was executed, but business impact could not be measured.',
                    status: 'ERROR'
                  });
                }
              });
            }
          },

          error: (error) => {

            console.error(
              'Action execution failed:',
              error
            );

            this.activity.unshift({
              message: 'Action execution failed',
              detail:
                'The action could not be executed.',
              status: 'ERROR'
            });
          }
        });
      },

      error: (error) => {

        console.error(
          'Guardrail check failed:',
          error
        );

        this.activity.unshift({
          message: 'Guardrail check failed',
          detail:
            'Unable to validate the action.',
          status: 'ERROR'
        });
      }
    });
  }

  /*
   * Manual impact measurement.
   * Can be used by a Measure Impact button in the HTML.
   */
  measureImpact(recommendation: any): void {

    this.dashboardService.measureImpact(
      recommendation.id
    ).subscribe({

      next: (impact) => {

        this.impactResult = impact;

        this.activity.unshift({
          message: 'Impact measured',
          detail:
            `Conversion improved by ${impact.improvement.toFixed(2)} percentage points. ` +
            `Estimated revenue impact: ₹${impact.estimatedRevenue.toFixed(2)}.`,
          status: 'IMPACT_MEASURED'
        });
      },

      error: (error) => {

        console.error(
          'Impact measurement failed:',
          error
        );

        this.activity.unshift({
          message: 'Impact measurement failed',
          detail:
            'Unable to measure business impact.',
          status: 'ERROR'
        });
      }
    });
  }

  /*
   * Run the Merchant Growth Agent.
   */
  runAgent(): void {

    this.agentRunning = true;
    this.agentResult = null;

    this.activity.unshift({
      message: 'Growth agent started',
      detail:
        'Analyzing merchant activity and determining the next best action.',
      status: 'RUNNING'
    });

    this.dashboardService.runAgent(
      'Find the most important merchant growth opportunity and determine the next action'
    ).subscribe({

      next: (result) => {

        this.agentResult = result;
        this.agentRunning = false;

        /*
         * Agent decision trace:
         * 1. Opportunity detection
         */
        if (result.opportunityType) {

          this.activity.unshift({
            message: 'Opportunity identified',
            detail:
              `${result.opportunityType} opportunity detected with ${result.opportunitySeverity} severity.`,
            status: 'DETECTED'
          });
        }

        /*
         * 2. Recommendation selection
         */
        if (result.recommendation) {

          this.activity.unshift({
            message: 'Recommendation selected',
            detail:
              `${result.recommendation} (Recommendation #${result.recommendationId})`,
            status: result.recommendationStatus
          });
        }

        /*
         * 3. Guardrail decision
         */
        if (result.guardrailDecision) {

          this.activity.unshift({
            message: 'Policy guardrail evaluated',
            detail:
              result.guardrailReason,
            status:
              result.guardrailDecision
          });
        }

        /*
         * 4. Action execution decision
         */
        if (result.actionType) {

          this.activity.unshift({
            message: 'Action decision',
            detail:
              `${result.actionType} → ${result.executionStatus}`,
            status:
              result.executionStatus
          });
        }

        /*
         * Agent completed
         */
        this.activity.unshift({
          message: 'Growth agent completed',
          detail: result.message,
          status: result.status
        });
      },

      error: (error) => {

        console.error(
          'Agent execution failed:',
          error
        );

        this.agentRunning = false;

        this.activity.unshift({
          message: 'Growth agent failed',
          detail:
            'Unable to complete the agent workflow.',
          status: 'ERROR'
        });
      }
    });
  }
}
