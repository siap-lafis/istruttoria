(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
//        .state('superficie-inverdimento', {
//            parent: 'entity',
//            url: '/superficie-inverdimento',
//            data: {
//                authorities: ['ROLE_USER'],
//                pageTitle: 'istruttoriaApp.superficieInverdimento.home.title'
//            },
//            views: {
//                'content@': {
//                    templateUrl: 'app/entities/superficie-inverdimento/superficie-inverdimentos.html',
//                    controller: 'SuperficieInverdimentoController',
//                    controllerAs: 'vm'
//                }
//            },
//            resolve: {
//                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
//                    $translatePartialLoader.addPart('superficieInverdimento');
//                    $translatePartialLoader.addPart('global');
//                    return $translate.refresh();
//                }]
//            }
//        })
        
        // Paginazione
        .state('superficie-inverdimento', {
            parent: 'entity',
            url: '/superficie-inverdimento?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.superficieInverdimento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/superficie-inverdimento/superficie-inverdimentos.html',
                    controller: 'SuperficieInverdimentoController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: {
                    value: null,
                    squash: true
                },
            },
            resolve: {
            	pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('superficieInverdimento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        
        .state('superficie-inverdimento-detail', {
            parent: 'entity',
            url: '/superficie-inverdimento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.superficieInverdimento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/superficie-inverdimento/superficie-inverdimento-detail.html',
                    controller: 'SuperficieInverdimentoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('superficieInverdimento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SuperficieInverdimento', function($stateParams, SuperficieInverdimento) {
                    return SuperficieInverdimento.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('superficie-inverdimento.new', {
            parent: 'superficie-inverdimento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/superficie-inverdimento/superficie-inverdimento-dialog.html',
                    controller: 'SuperficieInverdimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                supeSemi: null,
                                supePrimColt: null,
                                supeSecoColt: null,
                                supeAltrColt: null,
                                supePrimMax: null,
                                supeSecoMax: null,
                                supePrimDiff1: null,
                                supePrimDiff2: null,
                                tassoDiffPrim: null,
                                supePrimRidu: null,
                                supeSecoDiff1: null,
                                supeSecoDiff2: null,
                                tassoDiffSeco: null,
                                supeSecoRidu: null,
                                totaTassoDiff: null,
                                supeRiduDive: null,
                                supeEfa: null,
                                supeEfaObbl: null,
                                supeEfaDiff: null,
                                tassoDiffEfa: null,
                                supeRiduEfa: null,
                                totaRidu: null,
                                supePagaSemi: null,
                                supePratSens: null,
                                supePratNsens: null,
                                supePerm: null,
                                supeInve: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('superficie-inverdimento', null, { reload: true });
                }, function() {
                    $state.go('superficie-inverdimento');
                });
            }]
        })
        .state('superficie-inverdimento.edit', {
            parent: 'superficie-inverdimento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/superficie-inverdimento/superficie-inverdimento-dialog.html',
                    controller: 'SuperficieInverdimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SuperficieInverdimento', function(SuperficieInverdimento) {
                            return SuperficieInverdimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('superficie-inverdimento', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('superficie-inverdimento.delete', {
            parent: 'superficie-inverdimento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/superficie-inverdimento/superficie-inverdimento-delete-dialog.html',
                    controller: 'SuperficieInverdimentoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SuperficieInverdimento', function(SuperficieInverdimento) {
                            return SuperficieInverdimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('superficie-inverdimento', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
