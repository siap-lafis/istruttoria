(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('aduxstce', {
            parent: 'entity',
            url: '/aduxstce',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.aduxstce.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/aduxstce/aduxstces.html',
                    controller: 'AduxstceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('aduxstce');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('aduxstce-detail', {
            parent: 'entity',
            url: '/aduxstce/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.aduxstce.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/aduxstce/aduxstce-detail.html',
                    controller: 'AduxstceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('aduxstce');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Aduxstce', function($stateParams, Aduxstce) {
                    return Aduxstce.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('aduxstce.new', {
            parent: 'aduxstce',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aduxstce/aduxstce-dialog.html',
                    controller: 'AduxstceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idAttoAmmi: null,
                                idDecr: null,
                                numeDecr: null,
                                idEnte: null,
                                codiInte: null,
                                dataStor: null,
                                f100: null,
                                c110: null,
                                c109: null,
                                f200: null,
                                f201: null,
                                f202a: null,
                                f202b: null,
                                f202c: null,
                                f207: null,
                                f300: null,
                                c300a: null,
                                f300b: null,
                                c551: null,
                                c552: null,
                                c553: null,
                                c554: null,
                                c558: null,
                                c559: null,
                                c560: null,
                                c561: null,
                                c600: null,
                                c611: null,
                                c621: null,
                                c633: null,
                                c634: null,
                                c640: null,
                                unitMisu: null,
                                dataContOgge: null,
                                qntaLiqu: null,
                                impoLiqu: null,
                                riduRitaDepo: null,
                                riduModu: null,
                                discFina: null,
                                sanzCond: null,
                                statIstr: null,
                                liveAmmi: null,
                                userName: null,
                                dataAggi: null,
                                c640Qnta: null,
                                decoStat: null,
                                flagEsit: null,
                                dataScar: null,
                                c557: null,
                                c109a: null,
                                c400: null,
                                c401: null,
                                c402: null,
                                c403: null,
                                c404: null,
                                c405: null,
                                c406: null,
                                c407: null,
                                c558a: null,
                                c558b: null,
                                c558c: null,
                                c558d: null,
                                c558e: null,
                                c558f: null,
                                c620: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('aduxstce', null, { reload: true });
                }, function() {
                    $state.go('aduxstce');
                });
            }]
        })
        .state('aduxstce.edit', {
            parent: 'aduxstce',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aduxstce/aduxstce-dialog.html',
                    controller: 'AduxstceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Aduxstce', function(Aduxstce) {
                            return Aduxstce.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('aduxstce', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('aduxstce.delete', {
            parent: 'aduxstce',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aduxstce/aduxstce-delete-dialog.html',
                    controller: 'AduxstceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Aduxstce', function(Aduxstce) {
                            return Aduxstce.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('aduxstce', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
