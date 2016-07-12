(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('superficie', {
            parent: 'entity',
            url: '/superficies',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.superficie.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/superficie/superficies.html',
                    controller: 'SuperficieController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('superficie');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('superficie-detail', {
            parent: 'entity',
            url: '/superficies/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.superficie.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/superficie/superficie-detail.html',
                    controller: 'SuperficieDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('superficie');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Superficie', function($stateParams, Superficie) {
                    return Superficie.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('superficie.new', {
            parent: 'superficie',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/superficie/superficie-dialog.html',
                    controller: 'SuperficieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                codNazionale: null,
                                foglio: null,
                                codIntervento: null,
                                codColtura: null,
                                supeDich: null,
                                supeAmmi: null,
                                supeAmmiNetta: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('superficie', null, { reload: true });
                }, function() {
                    $state.go('superficie');
                });
            }]
        })
        .state('superficie.edit', {
            parent: 'superficie',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/superficie/superficie-dialog.html',
                    controller: 'SuperficieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Superficie', function(Superficie) {
                            return Superficie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('superficie', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('superficie.delete', {
            parent: 'superficie',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/superficie/superficie-delete-dialog.html',
                    controller: 'SuperficieDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Superficie', function(Superficie) {
                            return Superficie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('superficie', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('superficie.domanda', {
            parent: 'entity',
            url: '/superficies/domanda/{id}',
            data: {
                authorities: ['ROLE_USER'],
        		pageTitle: 'istruttoriaApp.superficie.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/superficie/superficies.html',
                    controller: 'SuperficiDomandaController',
                    controllerAs: 'vm'
                }
            },
	        resolve: {
	            translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
	                $translatePartialLoader.addPart('superficie');
	                $translatePartialLoader.addPart('global');
	                return $translate.refresh();
	            }]
	        }
        });
    }

})();
