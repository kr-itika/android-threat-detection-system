import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const ThreatDetectionApp());
}

/// MAIN APP
class ThreatDetectionApp extends StatelessWidget {

  const ThreatDetectionApp({super.key});

  @override
  Widget build(BuildContext context) {

    return MaterialApp(

      debugShowCheckedModeBanner: false,

      title: 'Security Analysis',

      theme: ThemeData(

        primarySwatch: Colors.blue,

        fontFamily: 'Roboto',
      ),

      home: const ThreatAnalysisScreen(),
    );
  }
}

/// MAIN SCREEN
class ThreatAnalysisScreen extends StatefulWidget {

  const ThreatAnalysisScreen({super.key});

  @override
  State<ThreatAnalysisScreen> createState() =>
      _ThreatAnalysisScreenState();
}

class _ThreatAnalysisScreenState
    extends State<ThreatAnalysisScreen> {

  /// Android Method Channel
  static const MethodChannel platform =
  MethodChannel('installed_apps');

  /// App List
  List<Map<String, dynamic>> _apps = [];

  /// Loading State
  bool _isLoading = false;

  /// Error Message
  String? _error;

  @override
  void initState() {

    super.initState();

    _fetchThreatAnalysis();
  }

  /// Fetch threat analysis from backend
  Future<void> _fetchThreatAnalysis() async {

    setState(() {

      _isLoading = true;

      _error = null;
    });

    try {

      final dynamic result =
      await platform.invokeMethod(
        'getPermissionAnalysis',
      );

      final List<Map<String, dynamic>> apps =
      List<Map<String, dynamic>>.from(

        (result as List).map(

              (item) =>
          Map<String, dynamic>.from(item),
        ),
      );

      setState(() {

        _apps = apps;

        _isLoading = false;
      });

    } catch (e) {

      setState(() {

        _error =
        'Failed to fetch security analysis.\n$e';

        _isLoading = false;
      });
    }
  }

  /// Risk Color Helper
  Color getRiskColor(String riskLevel) {

    switch (riskLevel) {

      case 'Safe':
        return Colors.green.shade800;

      case 'Low Risk':
        return Colors.orange.shade400;

      case 'Medium Risk':
        return Colors.orange.shade700;

      case 'High Risk':
        return Colors.red.shade700;

      case 'Critical Risk':
        return Colors.red.shade900;

      default:
        return Colors.grey;
    }
  }

  /// Build App Card
  Widget buildAppCard(
      Map<String, dynamic> app
      ) {

    final String riskLevel =
        app['threatLevel'] ?? 'Unknown';

    final int riskScore =
        app['riskScore'] ?? 0;

    final int securityScore =
        100 - riskScore;

    final List permissions =
        app['permissions'] ?? [];

    return Card(

      margin: const EdgeInsets.symmetric(
        horizontal: 14,
        vertical: 8,
      ),

      elevation: 3,

      color: Colors.white,

      shape: RoundedRectangleBorder(

        borderRadius:
        BorderRadius.circular(20),
      ),

      child: Padding(

        padding: const EdgeInsets.all(16),

        child: Column(

          crossAxisAlignment:
          CrossAxisAlignment.start,

          children: [

            /// TOP ROW
            Row(

              crossAxisAlignment:
              CrossAxisAlignment.start,

              children: [

                /// APP PLACEHOLDER ICON
                Container(

                  width: 52,
                  height: 52,

                  margin:
                  const EdgeInsets.only(
                    right: 14,
                  ),

                  decoration: BoxDecoration(

                    color:
                    Colors.blueGrey.shade50,

                    borderRadius:
                    BorderRadius.circular(14),
                  ),

                  child: Icon(

                    Icons.security,

                    color:
                    Colors.blueGrey.shade700,

                    size: 28,
                  ),
                ),

                /// APP DETAILS
                Expanded(

                  child: Column(

                    crossAxisAlignment:
                    CrossAxisAlignment.start,

                    children: [

                      /// APP NAME
                      Text(

                        app['appName']
                            ?? 'Unknown App',

                        style: const TextStyle(

                          fontSize: 19,

                          fontWeight:
                          FontWeight.w700,

                          letterSpacing: -0.2,
                        ),
                      ),

                      const SizedBox(height: 6),

                      /// RISK BADGE
                      Container(

                        padding:
                        const EdgeInsets.symmetric(
                          horizontal: 10,
                          vertical: 5,
                        ),

                        decoration: BoxDecoration(

                          color:
                          getRiskColor(
                              riskLevel),

                          borderRadius:
                          BorderRadius.circular(18),
                        ),

                        child: Text(

                          riskLevel,

                          style: const TextStyle(

                            color: Colors.white,

                            fontSize: 12,

                            fontWeight:
                            FontWeight.w600,
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),

            const SizedBox(height: 10),

            /// HIDDEN APP WARNING
            if (app['isHiddenApp'] == true)

              Padding(

                padding:
                const EdgeInsets.only(
                  bottom: 8,
                ),

                child: Row(

                  children: [

                    Icon(

                      Icons.visibility_off,

                      size: 18,

                      color:
                      Colors.red.shade700,
                    ),

                    const SizedBox(width: 8),

                    Text(

                      'Hidden Application',

                      style: TextStyle(

                        color:
                        Colors.red.shade700,

                        fontWeight:
                        FontWeight.w600,
                      ),
                    ),
                  ],
                ),
              ),

            /// SENSITIVE ACCESS TITLE
            const Text(

              'Sensitive Access',

              style: TextStyle(

                fontWeight:
                FontWeight.w600,

                fontSize: 15,
              ),
            ),

            const SizedBox(height: 8),

            /// NO PERMISSIONS
            if (permissions.isEmpty)

              Text(

                'No sensitive permissions detected',

                style: TextStyle(

                  color:
                  Colors.grey.shade700,

                  fontSize: 14,
                ),
              )

            else

            /// PERMISSION LIST
              ...permissions
                  .take(4)
                  .map<Widget>(

                    (permission) {

                  return Padding(

                    padding:
                    const EdgeInsets.only(
                      bottom: 6,
                    ),

                    child: Row(

                      children: [

                        Icon(

                          Icons.shield_outlined,

                          size: 15,

                          color:
                          Colors.grey.shade700,
                        ),

                        const SizedBox(width: 10),

                        Expanded(

                          child: Text(

                            permission[
                            'permissionName']
                                ?? 'Unknown',

                            style:
                            const TextStyle(
                              fontSize: 14,
                            ),
                          ),
                        ),
                      ],
                    ),
                  );
                },
              ),

            /// MORE PERMISSIONS
            if (permissions.length > 4)

              Padding(

                padding:
                const EdgeInsets.only(
                  top: 4,
                ),

                child: Text(

                  '+ ${permissions.length - 4} more',

                  style: TextStyle(

                    color:
                    Colors.grey.shade700,

                    fontSize: 13,
                  ),
                ),
              ),

            const SizedBox(height: 12),

            /// USAGE INSIGHTS
            const Text(

              'Usage Insights',

              style: TextStyle(

                fontWeight:
                FontWeight.w600,

                fontSize: 15,
              ),
            ),

            const SizedBox(height: 8),

            /// ACTIVE TIME
            Row(

              children: [

                Icon(

                  Icons.access_time,

                  size: 16,

                  color:
                  Colors.grey.shade700,
                ),

                const SizedBox(width: 8),

                Text(

                  '${((app['foregroundTime'] ?? 0) / 360000).toStringAsFixed(0)} hrs active today',

                  style: TextStyle(

                    color:
                    Colors.grey.shade700,
                  ),
                ),
              ],
            ),

            const SizedBox(height: 6),

            /// LAUNCH COUNT
            Row(

              children: [

                Icon(

                  Icons.touch_app,

                  size: 16,

                  color:
                  Colors.grey.shade700,
                ),

                const SizedBox(width: 8),

                Text(

                  '${app['launchCount'] ?? 0} activity sessions today',

                  style: TextStyle(

                    color:
                    Colors.grey.shade700,
                  ),
                ),
              ],
            ),

            const SizedBox(height: 12),

            /// SECURITY SCORE
            Text(

              'Security Score: '
                  '$securityScore / 100',

              style: TextStyle(

                color:
                getRiskColor(
                    riskLevel),

                fontWeight:
                FontWeight.w700,

                fontSize: 17,
              ),
            ),
          ],
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {

    /// LOADING UI
    if (_isLoading) {

      return Scaffold(

        appBar: AppBar(

          title: const Text(
            'Security Analysis',
          ),
        ),

        body: const Center(

          child:
          CircularProgressIndicator(),
        ),
      );
    }

    /// ERROR UI
    if (_error != null) {

      return Scaffold(

        appBar: AppBar(

          title: const Text(
            'Security Analysis',
          ),
        ),

        body: Center(

          child: Padding(

            padding:
            const EdgeInsets.all(20),

            child: Text(

              _error!,

              style: const TextStyle(

                color: Colors.red,

                fontSize: 16,
              ),
            ),
          ),
        ),
      );
    }

    /// MAIN UI
    return Scaffold(

      backgroundColor:
      const Color(0xFFF2F3F7),

      appBar: AppBar(

        elevation: 0,

        backgroundColor:
        const Color(0xFFF2F3F7),

        foregroundColor: Colors.black,

        title: const Text(

          'Security Analysis',

          style: TextStyle(

            fontWeight:
            FontWeight.w700,
          ),
        ),
      ),

      body:

      _apps.isEmpty

          ? const Center(

        child: Text(
          'No apps found.',
        ),
      )

          : ListView.builder(

        itemCount: _apps.length,

        itemBuilder:
            (context, index) {

          return buildAppCard(
            _apps[index],
          );
        },
      ),

      /// REFRESH BUTTON
      floatingActionButton:
      FloatingActionButton(

        backgroundColor: Colors.white,

        foregroundColor: Colors.black,

        elevation: 3,

        onPressed:
        _fetchThreatAnalysis,

        tooltip:
        'Refresh Analysis',

        child: const Icon(
          Icons.refresh,
        ),
      ),
    );
  }
}